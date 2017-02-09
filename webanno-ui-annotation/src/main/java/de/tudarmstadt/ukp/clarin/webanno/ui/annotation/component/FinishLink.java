/*
 * Copyright 2012
 * Ubiquitous Knowledge Processing (UKP) Lab and FG Language Technology
 * Technische Universität Darmstadt
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.tudarmstadt.ukp.clarin.webanno.ui.annotation.component;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.security.core.context.SecurityContextHolder;

import de.tudarmstadt.ukp.clarin.webanno.api.RepositoryService;
import de.tudarmstadt.ukp.clarin.webanno.api.UserDao;
import de.tudarmstadt.ukp.clarin.webanno.api.annotation.model.AnnotatorStateImpl;
import de.tudarmstadt.ukp.clarin.webanno.model.Mode;
import de.tudarmstadt.ukp.clarin.webanno.model.User;
import de.tudarmstadt.ukp.clarin.webanno.ui.annotation.dialog.YesNoFinishModalPanel;

/**
 * A link to close/finish annotation/correction/curation project
 *
 */
public class FinishLink
    extends Panel
{
    private static final long serialVersionUID = 3584950105138069924L;

    @SpringBean(name = "documentRepository")
    private RepositoryService repository;

    @SpringBean(name = "userRepository")
    private UserDao userRepository;

    ModalWindow yesNoModal;

    public FinishLink(String id, final IModel<AnnotatorStateImpl> aModel,
            final FinishImage finishImag)
    {
        super(id, aModel);

        final ModalWindow FinishModal;
        add(FinishModal = new ModalWindow("yesNoModal"));
        FinishModal.setOutputMarkupId(true);

        FinishModal.setInitialWidth(400);
        FinishModal.setInitialHeight(50);
        FinishModal.setResizable(true);
        FinishModal.setWidthUnit("px");
        FinishModal.setHeightUnit("px");
        FinishModal.setTitle("Are you sure you want to finish annotating?");

        AjaxLink<Void> showYesNoModal;

        add(showYesNoModal = new AjaxLink<Void>("showYesNoModal")
        {
            private static final long serialVersionUID = 7496156015186497496L;

            @Override
            public void onClick(AjaxRequestTarget target)
            {
                String username = SecurityContextHolder.getContext().getAuthentication().getName();
                User user = userRepository.get(username);
                if (FinishImage.isFinished(aModel, user, repository)) {
                    target.appendJavaScript("alert('Document already closed!')");
                }
                else {
                    FinishModal.setContent(new YesNoFinishModalPanel(FinishModal.getContentId(),
                            aModel.getObject(), FinishModal, Mode.ANNOTATION));
                    FinishModal.setWindowClosedCallback(new ModalWindow.WindowClosedCallback()
                    {
                        private static final long serialVersionUID = -1746088901018629567L;

                        @Override
                        public void onClose(AjaxRequestTarget target)
                        {
                            target.add(finishImag.setOutputMarkupId(true));
                            FinishLink.this.onClose(target);
                        }
                    });
                    FinishModal.show(target);
                }

            }
        });
        showYesNoModal.add(finishImag);
    }

    public void onClose(AjaxRequestTarget target) {
        // Do nothing by default
    }
}
